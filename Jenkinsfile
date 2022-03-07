pipeline {
    agent any
   
    environment {
        IDAITHALAM_EXECUTION_ENV = '${params.ENV_BUILD}'
    }

    parameters {
        string(name: 'ENV_BUILD', defaultValue: 'test-api', description: 'env specific build')
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
