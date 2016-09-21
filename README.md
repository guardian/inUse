inUse
=================================

[![CircleCI](https://circleci.com/gh/guardian/inUse.svg?style=svg)](https://circleci.com/gh/guardian/inUse)

This service tracks usage of services, by receiving http posts from them, and storing in DynamoDB

Results can then be interrogated via the frontend

Using the API
-------------

to create a new service (only need to do this once)

    curl -XPOST https://inuse.gutools.co.uk/api/v1/services/<YOUR SERVICE NAME>

register a call with a service (after it has been created)

    curl -XPOST -H "Content-Type:text/plain" -d "<ARBITRARY TEXT HERE>" https://inuse.gutools.co.uk/api/v1/services/<YOUR SERVICE NAME>/records
    
