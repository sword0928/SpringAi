pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "springai:latest"      // Spring Boot Docker 镜像名称
        COMPOSE_FILE = "docker-compose.yml"  // docker-compose 文件
        BRANCH = "dev"                        // GitHub 分支
    }

    stages {
        stage('Checkout') {
            steps {
                // 拉取 GitHub 代码，需要提前在 Jenkins 配置 SSH 凭证 github-ssh
                git branch: "${BRANCH}",
                    url: 'git@github.com:sword0928/SpringAi.git',
                    credentialsId: 'github-ssh'
            }
        }

        stage('Maven Build') {
            agent {
                // 使用 Maven 官方 Docker 镜像打包
                docker {
                    image '101.132.60.25:5000/maven:3.9.11'
                    args '-v /var/jenkins_home/.m2:/root/.m2'  // 持久化 Maven 本地仓库，加速依赖下载
                }
            }
            steps {
                sh '''
                  mvn clean package -DskipTests -B -T 1C
                  ls -lh target
                '''
                // 把 jar 带出 Docker agent
                stash name: 'jar', includes: 'target/*.jar'
            }
        }

        stage('Docker Build & Push') {
            steps {
                // 恢复jar
                unstash 'jar'
                sh '''
                  ls -lh target
                  docker build --platform=linux/arm64 -t ${DOCKER_IMAGE} .
                '''
                // 如果有私有仓库，可推送到远程
                // sh 'docker tag $DOCKER_IMAGE registry.cn-hangzhou.aliyuncs.com/yourrepo/springai:latest'
                // sh 'docker push registry.cn-hangzhou.aliyuncs.com/yourrepo/springai:latest'
            }
        }

        stage('Docker Compose Up') {
            steps {
                // 启动 Spring Boot + MySQL + Redis 服务
                sh '''
                        docker run --rm \
                          -v /var/run/docker.sock:/var/run/docker.sock \
                          -v "$PWD:$PWD" \
                          -w "$PWD" \
                          docker/compose:2.27.0 \
                          -f docker-compose.yml up -d --build
                        '''
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
