pipeline {
    agent any
    environment {
    	TOMCAT_HOME  = "E:\\Setup\\devtools\\apache-tomcat-8.0.53"
    }
    tools {
        maven 'Maven3'  // Tên đúng với cấu hình ở trên
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/anhnt3-seatech/servlet.git'
            }
        }
        stage('Pre-clean workaround') {
            steps {
            	echo 'Build and Deploy Successful!'
                bat 'del /F /Q target\\login-crud-servlet-0.0.1-SNAPSHOT.war'
            }
        }
        stage('Build WAR') {
            steps {
               bat 'mvn clean package -DskipTests'
            }
        }
        stage('Deploy to Tomcat') {
            steps {
                bat """
                    copy target\\*.war %TOMCAT_HOME %\\webapps\\login-crud-servlet.war /Y
                    %TOMCAT_HOME%\\bin\\shutdown.bat || exit 0
                    %TOMCAT_HOME%\\bin\\startup.bat
                """
                sleep(time: 15, unit: 'SECONDS') // đợi Tomcat khởi động lại
            }
        }
        stage('Run UI Tests') {
            steps {
                bat "mvn test -Dtest=com.example.ui.ItemCrudUITest"
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
    post {
        success {
            echo 'Build and Deploy Successful!'
        }
        failure {
            echo 'Build or Deploy failed, check the logs for details.'
        }
    }
}
