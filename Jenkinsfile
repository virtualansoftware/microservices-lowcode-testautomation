pipeline {
    agent any
   
    environment {
        IDAITHALAM_EXECUTION_ENV = 'test-db'
    }

    parameters {
        string(name: 'IDAITHALAM_EXECUTION_ENV', defaultValue: 'test-api', description: 'env specific build')
    }
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
