
DOCKER_IP=$(echo $DOCKER_HOST | cut -d":" -f2 | cut -d"/" -f3 )

echo $DOCKER_IP

echo "Configure Wechat callback to point to <tunnel url below>/wxcallback"

lt -l $DOCKER_IP -p 8080
