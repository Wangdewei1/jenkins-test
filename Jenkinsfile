pipeline {
    agent any

    tools {
        maven 'Maven3.8.4' // 这里的名字必须和你在 Jenkins 中配置的 Maven 名称一致
    }

    environment {
        REMOTE_HOST = "192.168.141.130"
        REMOTE_USER = "root"
        REMOTE_PASS = "123456"
        REMOTE_DIR  = "/home/project/test"
        JAR_NAME    = "jenkins-test.jar"
        // CONFIG_NAME = "deploy-vm" // SSH 配置名称
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
                            configName: 'deploy-vm',
                            transfers: [
                                sshTransfer(
                                    sourceFiles: 'target/*.jar',
                                    removePrefix: 'target',
                                    // remoteDirectory: '/home/project/test',
                                    // execCommand: 'nohup java -jar ${REMOTE_DIR}/jenkins-test.jar > ${REMOTE_DIR}/log.txt 2>&1 &'

                                    execCommand: "sh -c 'echo \"🛑 停止旧进程...\" && pkill -f jenkins-test.jar || true && sleep 2 && echo \"🚀 启动新服务...\" && nohup java -jar /home/project/test/jenkins-test.jar > /home/project/test/log.txt 2>&1 & echo \"✅ 部署完成！\"'"


                                    // execCommand: """
                                    // echo "🛑 停止旧进程..."
                                    // pkill -f ${JAR_NAME} || true
                                    // sleep 2
                                    // echo "🚀 启动新服务..."
                                    // nohup java -jar ${REMOTE_DIR}/${JAR_NAME} > ${REMOTE_DIR}/log.txt 2>&1 &
                                    // echo "✅ 部署完成！"
                                    // """

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
            echo "🎉 项目构建并部署成功！"
        }
        failure {
            echo "❌ 构建或部署失败，请检查日志输出。"
        }
    }
}