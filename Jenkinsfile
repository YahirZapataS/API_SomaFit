pipeline {
  agent any

  environment {
    DOCKERHUB_CREDENTIALS = credentials('dockerhub-creds')
    IMAGE_BACKEND = 'yahirzapatas/somafit-api'
    IMAGE_FRONTEND = 'yahirzapatas/somafit-ui'
  }

  stages {
    stage('Clonar Repositorio') {
      steps {
        git url: 'https://github.com/YahirZapataS/API_SomaFit.git', branch: 'main'
      }
    }

    stage('Compilar API') {
      steps {
        dir('.') {
          sh './mvnw clean package -DskipTests'
        }
      }
    }

    stage('Build UI') {
      steps {
        dir('somafit-ui') {
          sh 'npm install'
          sh 'npm run build'
        }
      }
    }

    stage('Construir Imágenes Docker') {
      steps {
        sh 'docker build -t $IMAGE_BACKEND .'
        sh 'docker build -t $IMAGE_FRONTEND ./somafit-ui'
      }
    }

    stage('Subir a DockerHub') {
      steps {
        withDockerRegistry([url: '', credentialsId: 'dockerhub-creds']) {
          sh 'docker push $IMAGE_BACKEND'
          sh 'docker push $IMAGE_FRONTEND'
        }
      }
    }

    stage('Desplegar API') {
      steps {
        sh '''
        docker stop somafit-api || true
        docker rm somafit-api || true
        docker run -d --name somafit-api -p 8080:8080 $IMAGE_BACKEND
        '''
      }
    }
  }

  post {
    success {
      echo '🚀 Despliegue exitoso desde GitHub hasta DockerHub + ejecución.'
    }
    failure {
      echo '❌ Falló el pipeline, revisa los logs.'
    }
  }
}