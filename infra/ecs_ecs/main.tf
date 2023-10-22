terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.aws_region
}

data "aws_subnets" "example" {
  filter {
    name   = "vpc-id"
    values = [var.vpc_id]
  }
}

resource "aws_security_group" "rds" {
  name   = "bszymkowiak_rds_postgres_sg"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_db_instance" "mariosy_test" {
  identifier             = "bszymkowiak-mariosy-db"
  instance_class         = "db.t3.micro"
  allocated_storage      = 20
  engine                 = "postgres"
  engine_version         = "15.3"
  db_name                = "mariosy"
  username               = var.db_user
  password               = var.db_password
  vpc_security_group_ids = [aws_security_group.rds.id]
  publicly_accessible    = true
  skip_final_snapshot    = true
}

resource "aws_cloudwatch_log_group" "mariosy_test_fe" {
  name = "/ecs/bszymkowiak-mariosy-backend"
}

resource "aws_cloudwatch_log_group" "mariosy_test_be" {
  name = "/ecs/bszymkowiak-mariosy-frontend"
}

resource "aws_ecs_cluster" "mariosy_test" {
  name = "bszymkowiak-mariosy-be-cluster"
}

resource "aws_ecs_task_definition" "mariosy_test_be" {
  family                   = "bszymkowiak-mariosy-backend"
  requires_compatibilities = ["FARGATE"]
  runtime_platform {
    operating_system_family = "LINUX"
    cpu_architecture        = "X86_64"
  }
  network_mode             = "awsvpc"
  cpu                      = "512"
  memory                   = "1024"

  execution_role_arn       = var.ecs_task_execution_role_arn
  task_role_arn            = var.ecs_task_execution_role_arn

  container_definitions    = <<DEFINITION
    [
      {
        "name": "${var.ecr_be_name}",
        "image": "${var.ecr_image_be_uri}",
        "cpu": 512,
        "memory": 1024,
        "essential": true,

        "environment": [
              {"name": "DB_PASSWORD", "value": "${var.db_password}"},
              {"name": "DB_HOST", "value": "${aws_db_instance.mariosy_test.address}"}
            ],

        "logConfiguration": {
          "logDriver": "awslogs",
          "options": {
            "awslogs-group": "${aws_cloudwatch_log_group.mariosy_test_be.name}",
            "awslogs-region": "${var.aws_region}",
            "awslogs-stream-prefix": "ecs"
          }
        },
     "portMappings": [
          {
            "containerPort": 80,
            "hostPort": 80
          }
        ]
      }
  ]
  DEFINITION
}

resource "aws_security_group" "mariosy_test_backend" {
  name   = "bszymkowiak_mariosy_backend_sg"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8083
    to_port     = 8083
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]

  }

  egress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

}

resource "aws_ecs_service" "mariosy_test_be" {
  name            = "mariosy_backend_service"
  cluster         = aws_ecs_cluster.mariosy_test.id
  task_definition = aws_ecs_task_definition.mariosy_test_be.arn
  desired_count   = 1
  launch_type = "FARGATE"

    network_configuration {
      subnets          = data.aws_subnets.example.ids
      security_groups  = [aws_security_group.mariosy_test_backend.id]
      assign_public_ip = true
    }
}

#
# STAGE 2
#

resource "aws_ecs_task_definition" "mariosy_test_fe" {
  family                   = "bszymkowiak-mariosy-frontend"
  requires_compatibilities = ["FARGATE"]
  runtime_platform {
    operating_system_family = "LINUX"
    cpu_architecture        = "X86_64"
  }
  network_mode             = "awsvpc"
  cpu                      = "512"
  memory                   = "1024"

  execution_role_arn       = var.ecs_task_execution_role_arn
  task_role_arn            = var.ecs_task_execution_role_arn

  container_definitions    = <<DEFINITION
    [
      {
        "name": "${var.ecr_fe_name}",
        "image": "${var.ecr_image_fe_uri}",
        "cpu": 512,
        "memory": 1024,
        "essential": true,

        "environment": [
              {"name": "NG_BACKEND_API_URL", "value": "${var.backend_task_public_api_ip}"}
            ],

        "logConfiguration": {
          "logDriver": "awslogs",
          "options": {
            "awslogs-group": "${aws_cloudwatch_log_group.mariosy_test_fe.name}",
            "awslogs-region": "${var.aws_region}",
            "awslogs-stream-prefix": "ecs"
          }
        },
     "portMappings": [
          {
            "containerPort": 80,
            "hostPort": 80
          }
        ]
      }
  ]
  DEFINITION
}


resource "aws_security_group" "mariosy_test_frontend" {
  name   = "bszymkowiak_mariosy_frontend_sg"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]

  }

  egress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 8083
    to_port     = 8083
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_ecs_service" "mariosy_test_fe" {
  name            = "mariosy_frontend_service"
  cluster         = aws_ecs_cluster.mariosy_test.id
  task_definition = aws_ecs_task_definition.mariosy_test_fe.arn
  desired_count   = 1
  launch_type = "FARGATE"

  network_configuration {
    subnets          = data.aws_subnets.example.ids
    security_groups  = [aws_security_group.mariosy_test_frontend.id]
    assign_public_ip = true
  }
}





