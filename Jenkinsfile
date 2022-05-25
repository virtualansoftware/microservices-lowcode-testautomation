pipeline {
    agent any


    tools {
        maven "Maven"
    }

    stages {

        stage ('Clean Stage') {
            steps {
                  bat 'mvn clean'
            }
        }
        stage ('Testing Stage') {
            steps {

                    bat 'mvn generate-sources test'
            }
        }


    }

    post {
        always {
            cucumber '**/cucumber-*.json'

        }
    }

}
