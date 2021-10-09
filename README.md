Generate Java code from protobuffer:

```protoc -I=. --java_out=./core/src/main/java ./core/src/main/proto/message.proto```


Ważne:
W ramach kilka DistributedThread w tej samej instancji programu nie można używać tego samego ID monitora. (Można za to używać monitorów o różnym ID między DistributedThread'ami)