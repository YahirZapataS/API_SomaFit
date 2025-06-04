pipeline {
  agent any

  environment {
    DOCKERHUB_CREDENTIALS = credentials('dockerhub-creds') // ID configurado en Jenkins
    IMAGE_BACKEND = 'tuusuario/somafit-api'
    IMAGE_FRONTEND = 'tuusuario/somafit-ui'
  }

  stages {
    stage('Compilar Backend') {
      steps {
        dir('.') {
          sh './mvnw clean package -DskipTests'
        }
      }
    }

    stage('Compilar Frontend') {
      steps {
        dir('somafit-ui') {
          sh 'npm install'
          sh 'npm run build'
        }
      }
    }

    stage('Build Docker Images') {
      steps {
        sh 'docker build -t $IMAGE_BACKEND .'
        sh 'docker build -t $IMAGE_FRONTEND ./somafit-ui'
      }
    }

    stage('Push a DockerHub') {
      steps {
        withDockerRegistry([url: '', credentialsId: 'dockerhub-creds']) {
          sh 'docker push $IMAGE_BACKEND'
          sh 'docker push $IMAGE_FRONTEND'
        }
      }
    }
  }

  post {
    success {
      echo '✅ Despliegue completo.'
    }
    failure {
      echo '❌ Algo falló. Verifica logs.'
    }
  }
}