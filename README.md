## Requirements

### Local environment

1. Run docker-compose from tools catalog with command:
   <b>docker-compose up</b> 
   it will prepare database for application;
   
   * DB username: user
   * DB password: password
   
2. Run application with program argument:
   <b> spring.profiles.active=local </b>
   
### Performance tests

⚠️  Apache benchmark need to be installed to run performance tests.

1. Run docker-compose from tools catalog with command:
      <b>docker-compose up</b>
   
2. Run GitHub service mock from tools catalog with command:

   <b> docker-compose -f docker-compose-github-mock.yml up </b>

   it will return user for login: <b> octocat </b>

3. Run application with program argument:
   <b> spring.profiles.active=performance-tests </b>
   




   <b> ⚠️ Mock was prepared because github has access rate limits described here: 
   <a href="https://docs.github.com/en/rest/overview/resources-in-the-rest-api#rate-limiting "> Github: access rate 
   limits </a>
   </b>

3. Run tests from performance-test catalog named:

   <b> tests_request_count_counting.sh </b>
   
   tests summary will be saved in the same catalog with file name <b> result.txt </b>