pipeline {
    agent any

    environment {
        REMOTE_HOST = "192.168.141.130"
        REMOTE_USER = "root"
        REMOTE_PASS = "123456"
        REMOTE_DIR  = "/home/project/test"
        JAR_PATTERN = "jenkins-test-*.jar"
        JAR_NAME    = "jenkins-test.jar"
    }

    stages {
        stage('拉取代码') {
            steps {
                git url: 'https://github.com/Wangdewei1/jenkins-test.git'
            }
        }

        stage('Maven 构建') {
            steps {
                sh 'mvn clean package -Dmaven.test.skip=true'
            }
        }

        stage('上传并部署到远程服务器') {
            steps {
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'deploy-vm', // Jenkins 系统配置的 SSH 名称
                            transfers: [
                                sshTransfer(
                                    sourceFiles: "target/${JAR_PATTERN}",
                                    removePrefix: 'target',
                                    remoteDirectory: "${REMOTE_DIR}",
                                    execCommand: """
                                    # 杀掉旧进程
                                    PID=\$(ps -ef | grep ${JAR_NAME} | grep -v grep | awk '{print \$2}')
                                    if [ -n "\$PID" ]; then
                                      echo "⚠️ 发现旧进程 PID: \$PID，尝试终止..."
                                      kill -9 \$PID
                                    fi

                                    # 拷贝 jar 为统一名
                                    cp ${REMOTE_DIR}/${JAR_PATTERN} ${REMOTE_DIR}/${JAR_NAME}

                                    # 启动新进程
                                    nohup java -jar ${REMOTE_DIR}/${JAR_NAME} > ${REMOTE_DIR}/log.txt 2>&1 &
                                    """
                                )
                            ],
                            usePromotionTimestamp: false,
                            verbose: true
                        )
                    ]
                )
            }
        }
    }

    post {
        success {
            echo "🎉 项目构建并成功部署到 ${REMOTE_HOST}！"
        }
        failure {
            echo "❌ 构建或部署失败，请检查日志输出。"
        }
    }
}
