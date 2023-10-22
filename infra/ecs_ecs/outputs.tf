output "rds_hostname" {
  description = "RDS instance hostname"
  value       = aws_db_instance.mariosy_test.address
  sensitive   = false
}

