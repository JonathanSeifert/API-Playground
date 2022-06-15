pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
    post {
        always {
            emailext body: 'Insert text here.', subject: 'Pipeline Status', to: 'jonathanseifert1998@gmail.com'
        }
    }
}
