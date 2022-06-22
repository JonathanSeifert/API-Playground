pipeline {
    agent {
        docker {
            image 'maven:3.8.6-openjdk-18' 
            args '-v /root/.m2:/root/.m2' 
        }
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh 'mvn -B -DskipTests clean package' 
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                sh 'mvn clean test' 
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
