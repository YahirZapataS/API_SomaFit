pipeline {
    agent none
    stages {
        stage('Maven Install') {
            agent {
                docker {
                    image 'maven:3.9.9'
                }
            }

            steps {
                sh 'mvn clean install'
            }
        }

        stage('Docker Build') {
            agent any
            steps {
                sh 'docker build -t yahirzapatas/somafit-api:latest .'
            }
        }

        stage('Docker Push') {
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'dockerhubPassword', usernameVariable: 'dockerhubUser')]) {
                    sh "docker login -u ${env.dockerhubUser} -p ${env.dockerhubPassword}"
                    sh 'docker push yahirzapatas/somafit-api:latest'
                }
            }
        }
    }
}