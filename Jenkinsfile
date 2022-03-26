pipeline {
    agent any


    tools {
        maven "Maven"
    }

    stages {

        stage ('Clean Stage') {
            steps {
                  sh 'mvn clean'
            }
        }
        stage ('Testing Stage') {
            steps {

                    sh 'mvn test'
            }
        }


    }

    post {
        always {
            cucumber '**/cucumber-*.json'

        }
    }

}
