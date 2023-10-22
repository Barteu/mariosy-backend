variable "vpc_id" {
  description = "VPC id where to deploy platform."
  type        = string
  default     = "vpc-032bcd1bef888a4f1"
}

variable "aws_region" {
  description = "Aws region "
  sensitive   = false
  default = "eu-central-1"
}

variable "db_password" {
  description = "RDS root user password"
  sensitive   = true
}

variable "db_user" {
  description = "RDS root user username"
  sensitive   = false
  default = "postgresadmin"
}

variable "ecr_be_name" {
  description = "ECR backend repo name"
  sensitive   = false
  default = "bszymkowiak-mariosy-backend"
}

variable "ecr_fe_name" {
  description = "ECR frontend repo name"
  sensitive   = false
  default = "bszymkowiak-mariosy-frontend"
}

variable "ecr_be_uri" {
  description = "ECR backend repo URI"
  sensitive   = false
  default = "710642560495.dkr.ecr.eu-central-1.amazonaws.com/bszymkowiak-mariosy-backend"
}

variable "ecr_fe_uri" {
  description = "ECR frontend repo URI"
  sensitive   = false
  default = "710642560495.dkr.ecr.eu-central-1.amazonaws.com/bszymkowiak-mariosy-frontend"
}

variable "ecr_image_be_uri" {
  description = "ECR backend image URI"
  sensitive   = false
  default = "710642560495.dkr.ecr.eu-central-1.amazonaws.com/bszymkowiak-mariosy-backend:latest"
}

variable "ecr_image_fe_uri" {
  description = "ECR frontend image URI"
  sensitive   = false
  default = "710642560495.dkr.ecr.eu-central-1.amazonaws.com/bszymkowiak-mariosy-frontend:latest"
}

variable "ecs_task_execution_role_arn" {
  description = "ECR frontend image URI"
  sensitive   = false
  default = "arn:aws:iam::710642560495:role/ecsTaskExecutionRole"
}

variable "backend_task_public_api_ip" {
  description = "Backend task public api ip"
  sensitive   = false
  default = "http://3.71.53.131:8083/api/v1/"
}