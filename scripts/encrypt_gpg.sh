#!/bin/sh

gpg --symmetric --cipher-algo AES256 ../secrets.properties
