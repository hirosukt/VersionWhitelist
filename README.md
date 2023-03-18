# VersionWhitelist
Restrict player connections by their version.

## Features
* Per server whitelisting
* Customizable kick message

## Config
The plugin determines version by protocol number.  
Please reference this page: https://wiki.vg/Protocol_version_numbers
```yaml
whitelist:
  server1:
    - 5
    - 47
  server2:
    - 762
# You can use color code with &
kick-message: "&cYou are using unsupported version!"
```