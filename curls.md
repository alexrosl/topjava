curl -XGET -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/meals'
curl -XGET -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/meals/100002'
curl -XPUT -H "Content-type: application/json" -d '{
   "id": 100002,
   "dateTime": "2015-05-30T10:00:00",
   "description": "Updated breakfast",
   "calories": 300,
   "user": null
}' 'http://localhost:8080/topjava/rest/meals/100002'
curl -XDELETE -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/meals/100002'
curl -XPOST -H "Content-type: application/json" -d '{
   "dateTime": "2019-12-31T23:00:00",
   "description": "Happy New Year eats",
   "calories": 4500,
   "user": null
}' 'http://localhost:8080/topjava/rest/meals'
curl -XGET -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/meals/filter?dateFrom=2015-05-30&dateTo=2015-05-30'
curl -XGET -H "Content-type: application/json" 'http://localhost:8080/topjava/rest/meals/filter'