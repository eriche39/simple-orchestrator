# Samples
Two sample RESTful services are provided.
1. Card application service simulates credit/debit card application service.
2. Loan application service.

## Start the application:
run SampleApplication

Note: for simplicity, validation are not complete.

## Services:

#### 1. Calling card application service with credit card application:

curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 1afc1cad-5ea3-c618-2ef1-3ee4f38c7b0c" -d '{
	"person" : {
		"name" : "lawrence",
		"ssn": "424222444",
		"address":{
			"zip" : "92131"
		}
	},
	"occupation" : "engineer",
	"employed": true,
	"cardType": "Credit"
}' "http://localhost:8080/application/card"

#### 2. Calling card application service with debit card application:

curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: cd7f6813-38b6-12e2-6daf-9f358fc475b2" -d '{
	"person" : {
		"name" : "lawrence",
		"ssn": "424222444",
		"address":{
			"zip" : "92131"
		}
	},
	"occupation" : "engineer",
	"employed": true,
	"cardType": "Debit"
}' "http://localhost:8080/application/card"

#### 3. Calling card application service with black listed ssn:

curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 5be4db59-d07f-feb4-2463-9564506675c2" -d '{
	"person" : {
		"name" : "lawrence",
		"ssn": "999999999",
		"address":{
			"zip" : "92131"
		}
	},
	"occupation" : "engineer",
	"employed": true,
	"cardType": "Credit"
}' "http://localhost:8080/application/card"

#### 4. Calling loan application service:

curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 985c3487-5cb1-820f-f599-ade20204d154" -d '{
	"person" : {
		"name" : "lawrence",
		"SSN": "424222444",
		"address":{
			"zip" : "92131"
		}
	},
	"occupation" : "engineer",
	"salary": 90000,
	"loanAmount": 200000
}' "http://localhost:8080/application/loan"

#### 5. Browse to: http://localhost:8080/internal/meta
all runtime orchestrators' components will show in a json format

## Visualization 
#### Browse to: http://localhost:8080/internal/orchestrators.html
UI powered with Mermaid.js will retrieve run time orchestrators meta data and render their structure in a flow chart style.

<img src="https://raw.githubusercontent.com/eriche39/simple-orchestrator/master/samples/visual.png" width=490 height=520 />
