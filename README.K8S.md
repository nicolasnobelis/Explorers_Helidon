###### Installation for Kubernetes

Make Minikube docker the default one for the shell

`eval $(minikube -p minikube docker-env)`

Install the docker private registry in Minikube's docker 
(this needs to be done only once and will be automatically 
restarted on reboot)

`docker-compose -f docker_registry.yml up -d`

Build the docker image for **explorers-helidon**
`docker build -t nino/distroless-explorers-helidon:0.1 .`

The image can be tested

`docker run -ti -p8080:8080 nino/distroless-explorers-helidon:0.1`

`curl -X GET http://$(minikube ip):8080/rest/explorer/list`

Push the built image to the private registry

`docker tag <source image id> localhost:5000/nino/distroless-explorers-helidon:0.1`

`docker push localhost:5000/nino/distroless-explorers-helidon:0.1`

Test the private registry has the image

`curl http://$(minikube ip):5000/v2/_catalog`

Deploy the service in K8S

`kubectl apply -f ../Explorers/Explorers\ Helidon/app.yaml`

You can test with the proxy

`export POD_NAME=$(kubectl get pods -l app=explorers-mp -o=jsonpath='{range .items[0]}{.metadata.name}{"\n"}{end}')`

`curl http://localhost:8001/api/v1/namespaces/default/pods/$POD_NAME:8080/proxy/rest/explorer/list`

Or by querying the service

`curl -X GET http://$(minikube ip):30000/rest/explorer/list`

The service is also available in the cluster on port 8081