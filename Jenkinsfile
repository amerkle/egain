pipeline {
  agent any
  stages {
    stage('Wait') {
      parallel {
        stage('') {
          steps {
            sleep 3
          }
        }
        stage('hello world') {
          steps {
            echo 'hello world'
          }
        }
      }
    }
    stage('Send Mail') {
      steps {
        mail(subject: 'hi', body: 'blubb', from: 'amr@bsi-software.com', to: 'merkle.adrian@gmail.com')
      }
    }
  }
}