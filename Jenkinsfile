pipeline {
    agent any

    environment {
        AWS_REGION = 'us-east-1'
        LAMBDA_FUNCTION_NAME = 'albertfunction'
        S3_BUCKET = 'albertawsbucket'
        S3_KEY = '/Users/binliu/projects/spring-cloud/aws_lambda/LambdaS3Example/target/LambdaS3Example-1.0-SNAPSHOT.jar'
    }

   tools {
        maven 'apache-maven-3.9.7'
   }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the repository containing the Lambda function code
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Build the Lambda function JAR
                sh 'mvn clean package'
            }
        }

        stage('Upload to S3') {
            steps {
                withAWS(credentials: '6059e44e-ab73-4d2e-b139-5d5630c319a7', region: "${AWS_REGION}") {
                    s3Upload(
                        bucket: "${S3_BUCKET}",
                        file: 'target/LambdaS3Example-1.0-SNAPSHOT.jar',
                        path: "${S3_KEY}"
                    )
                }
            }
        }

        stage('Update Lambda Function') {
            steps {
                withAWS(credentials: '6059e44e-ab73-4d2e-b139-5d5630c319a7', region: "${AWS_REGION}") {
                    script {
                        // Update the Lambda function code with the new JAR file
                        sh """
                        aws lambda update-function-code \
                        --function-name ${LAMBDA_FUNCTION_NAME} \
                        --s3-bucket ${S3_BUCKET} \
                        --s3-key ${S3_KEY}
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}

