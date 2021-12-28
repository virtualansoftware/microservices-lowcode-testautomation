pipeline {
    agent any


    tools {
        maven "Maven"
    }

    stages {

        stage ('Compile Stage') {
            steps {
                  bat 'mvn clean compile'
            }
        }
        stage ('Testing Stage') {
            steps {

                    bat 'mvn test'
            }
        }


    }

    post {
        always {
            cucumber '**/cucumber-*.json'

        }
    }

}
