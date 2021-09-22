Generate Java code from protobuffer:

```protoc -I=. --java_out=./core/src/main/java ./core/src/main/proto/message.proto```