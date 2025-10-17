pipeline {
    agent any

    tools {
        jdk 'Java21'
        maven 'Maven'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'dev',
                    url: 'https://github.com/Elverovski/QuoiRegarder_backend'
            }
        }

        stage('Build & Package') {
            steps {
                sh 'mvn clean package -DskipOpenApi=true'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test -DskipOpenApi=true'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        success {
            mail to: 'elverovsky@gmail.com,bobodakes827@gmail.com',
                 subject: "Pipeline réussi",
                 body: "Le build et les tests sont terminés avec succès."
        }
        failure {
            mail to: 'elverovsky@gmail.com,bobodakes827@gmail.com',
                 subject: "Pipeline échoué",
                 body: "Une erreur est survenue pendant le build ou les tests."
        }
    }
}
