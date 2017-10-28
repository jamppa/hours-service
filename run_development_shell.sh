#!/usr/bin/env bash
docker-compose -f docker/development.yml run --rm --service-ports --name hours-dev-shell hours-service /bin/bash
