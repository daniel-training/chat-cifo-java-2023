#!/bin/bash

################################################################################
## gen_asymmetric_keys.sh                                                     ##
##   params:                                                                  ##
##     $1: (optional) custom keys name (without extension). The script always ##
##         set ".key" extension for the private key and ".pub" extension for  ##
##         the public key.                                                    ##
##                                                                            ##
##         If not custom name provided, the default name are used.            ##
##                                                                            ##
##   usage examples:                                                          ##
##     - generating keys with default names:                                  ##
##         ./gen_asymmetric_keys.sh                                           ##
##     - generating keys with custom names                                    ##
##         ./gen_asymmetric_keys.sh my_custom_jwt_rsa_pem                     ##
##                                                                            ##
## This script generates a pair of asymmetric RSA keys PEM-encoded PKCS#8.    ##
## Some version of openssl generates PKCS#1 by default. The script detects    ##   
## if the generated key is in PKCS#1 and converts it to PKCS#8.               ##
##                                                                            ##
##                                                                            ##
## Author: danvazmem                                                          ##
##                                                                            ##
################################################################################

#set -x

WORK_DIRECTORY=`pwd`
SCRIPT_DIRECTORY=`dirname $0`

#########################################R######################################
## Function use_keys_directory()                                              ##
##   params:                                                                  ##
##     $1: keys directory to use. If not exist, try to create it.             ##
##                                                                            ##
##                                                                            ##
## Changes to or creates the keys directory.                                  ##
##                                                                            ##
################################################################################
use_keys_directory(){
  RESULT=1

  if [ "$#" -ne 0 ]
  then
    if [ -d "$1" ]
    then
      cd "$1"
      RESULT=0
    else
      if mkdir -p "$1"
      then
        cd "$1"
        RESULT=0
      else
        echo "Can not create the $1 keys directory where generate the keys."
        RESULT=1
      fi
    fi

  fi

  return $RESULT
}

################################################################################
## Function generate_keys()                                                   ##
##   params: --                                                               ##
##     $1: custom keys name (without extension)                               ##
##         function sets ".key" extension for the private key                 ## 
##         and ".pub" extension for the public key.                           ##
##                                                                            ##
##                                                                            ##
## Generate a pair of asymmetric RSA keys PEM-encoded PKCS#8.                 ##
## Some version of openssl generates PKCS#1 by default. The function detects  ##   
## if the generated key is in PKCS#1 and converts it to PKCS#8.               ##
##                                                                            ##
################################################################################
generate_keys(){

  RESULT=1
  OPENSSL=openssl  
  HEAD=head
  GREP=grep
  MV=mv
  RM=rm
  
  PKCS1="-----BEGIN RSA PRIVATE KEY-----"
  PKCS8="-----BEGIN PRIVATE KEY-----"
  
  KEYS_DIRECTORY="keys"
  PRIVATE_KEY_NAME="${1:-dev-chat_jwt_rsa_pem}"
  PRIVATE_KEY_EXTENSION="key"
  PUBLIC_KEY_NAME="${1:-dev-chat_jwt_rsa_pem}"
  PUBLIC_KEY_EXTENSION="pub"
  
  PRIVATE_KEY_FILE="${PRIVATE_KEY_NAME}.${PRIVATE_KEY_EXTENSION}"
  PUBLIC_KEY_FILE="${PUBLIC_KEY_NAME}.${PUBLIC_KEY_EXTENSION}"
  
  # Test for keys directory and uses it
  if use_keys_directory "${SCRIPT_DIRECTORY}/../${KEYS_DIRECTORY}"
  then

    if [ ! -e "$PRIVATE_KEY_FILE" ]
    then

      echo "Generating RSA private key ..."
      $OPENSSL genrsa -out "$PRIVATE_KEY_FILE" 2048    
    
      if $HEAD -n 1 "$PRIVATE_KEY_FILE" | $GREP -q -F -- "$PKCS1"
      then
      	echo "Converting PKCS#1 to PKCS#8 ..."
        $MV "$PRIVATE_KEY_FILE" "$PRIVATE_KEY_FILE.pkcs1"
        $OPENSSL pkcs8 -inform pem -in "$PRIVATE_KEY_FILE.pkcs1" -topk8 -nocrypt -outform pem -out "$PRIVATE_KEY_FILE"
        $RM "$PRIVATE_KEY_FILE.pkcs1"
      fi

      echo "Generating RSA public key ..."
      $OPENSSL rsa -in "$PRIVATE_KEY_FILE" -outform PEM -pubout -out "$PUBLIC_KEY_FILE"
      
    else
      echo "File $PRIVATE_KEY_FILE already exists. Make sure you want to delete that private key and delete it manually."
      RESULT=1
    fi
    RESULT=0
  else
    RESULT=1
  fi

  return $RESULT
}

# main #########################################################################

generate_keys "$1"
#set +x

exit $?

