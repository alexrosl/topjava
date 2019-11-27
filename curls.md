##### cursl для проверки Users
###### Admin
- curl -XGET -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/admin/users'
- curl -XPOST -H "Content-type: application/json" -d '{"name": "New2",
   "email": "new2@yandex.ru",
   "password": "passwordNew",
   "roles": ["ROLE_USER"]
  }' 'http://localhost:8080/topjava/rest/admin/users'
- curl -XGET -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/admin/users/100000'

###### Profile
- curl -XGET -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/profile'
- curl -XPUT -H "Content-type: application/json" -d '{"name": "New777",
   "email": "new777@yandex.ru",
   "password": "passwordNew",
   "roles": ["ROLE_USER"]
  }' 'http://localhost:8080/topjava/rest/profile'
- curl -XDELETE -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/profile'

##### curls для проверки Meals
- curl -XGET -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/meals'
- curl -XGET -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/meals/100002'
- curl -XPUT -H "Content-type: application/json" -d '{
   "id": 100002,
   "dateTime": "2015-05-30T10:00:00",
   "description": "Updated breakfast",
   "calories": 300,
   "user": null
}' 'http://localhost:8080/topjava/rest/meals/100002'
- curl -XDELETE -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/meals/100002'
- curl -XPOST -H "Content-type: application/json" -d '{
   "dateTime": "2019-12-31T23:00:00",
   "description": "Happy New Year eats",
   "calories": 4500,
   "user": null
}' 'http://localhost:8080/topjava/rest/meals'
- curl -XGET -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/meals/filter?dateFrom=2015-05-30&dateTo=2015-05-30'
- curl -XGET -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/meals/filter'