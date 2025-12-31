pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "springai:latest"  // Docker 镜像名称
        COMPOSE_FILE = "docker-compose.yml"
    }

    stages {
        stage('Checkout') {
            steps {
                // 从 GitHub 拉取代码
                git branch: 'dev',
                    url: 'git@github.com:sword0928/SpringAi.git',
                    credentialsId: 'github-ssh'
            }
        }

        stage('Build Maven') {
            steps {
                // 使用 Maven 打包 Spring Boot 项目
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build & Push') {
            steps {
                // 构建 Docker 镜像
                sh 'docker build -t $DOCKER_IMAGE .'
                // 如果有私有仓库，可推送到远程
                // sh 'docker tag $DOCKER_IMAGE registry.cn-hangzhou.aliyuncs.com/yourrepo/springai:latest'
                // sh 'docker push registry.cn-hangzhou.aliyuncs.com/yourrepo/springai:latest'
            }
        }

        stage('Docker Compose Up') {
            steps {
                // 启动 Spring Boot + MySQL + Redis 服务
                sh 'docker-compose -f $COMPOSE_FILE up -d --build'
            }
        }
    }

    post {
        always {
            // 构建完成后显示容器状态
            sh 'docker ps -a'
        }
    }
}
