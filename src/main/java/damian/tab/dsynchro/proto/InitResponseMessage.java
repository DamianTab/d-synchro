// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/message.proto

package damian.tab.dsynchro.proto;

/**
 * <pre>
 *req-rep socket - portmapper
 * </pre>
 *
 * Protobuf type {@code synchro.InitResponseMessage}
 */
public final class InitResponseMessage extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:synchro.InitResponseMessage)
    InitResponseMessageOrBuilder {
private static final long serialVersionUID = 0L;
  // Use InitResponseMessage.newBuilder() to construct.
  private InitResponseMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private InitResponseMessage() {
    portMapperAddress_ = "";
    addresses_ = com.google.protobuf.LazyStringArrayList.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new InitResponseMessage();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private InitResponseMessage(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            processID_ = input.readInt32();
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            portMapperAddress_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              addresses_ = new com.google.protobuf.LazyStringArrayList();
              mutable_bitField0_ |= 0x00000001;
            }
            addresses_.add(s);
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        addresses_ = addresses_.getUnmodifiableView();
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return damian.tab.dsynchro.proto.ProtoMessages.internal_static_synchro_InitResponseMessage_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return damian.tab.dsynchro.proto.ProtoMessages.internal_static_synchro_InitResponseMessage_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            damian.tab.dsynchro.proto.InitResponseMessage.class, damian.tab.dsynchro.proto.InitResponseMessage.Builder.class);
  }

  public static final int PROCESSID_FIELD_NUMBER = 1;
  private int processID_;
  /**
   * <code>int32 processID = 1;</code>
   * @return The processID.
   */
  @java.lang.Override
  public int getProcessID() {
    return processID_;
  }

  public static final int PORTMAPPERADDRESS_FIELD_NUMBER = 2;
  private volatile java.lang.Object portMapperAddress_;
  /**
   * <code>string portMapperAddress = 2;</code>
   * @return The portMapperAddress.
   */
  @java.lang.Override
  public java.lang.String getPortMapperAddress() {
    java.lang.Object ref = portMapperAddress_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      portMapperAddress_ = s;
      return s;
    }
  }
  /**
   * <code>string portMapperAddress = 2;</code>
   * @return The bytes for portMapperAddress.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getPortMapperAddressBytes() {
    java.lang.Object ref = portMapperAddress_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      portMapperAddress_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ADDRESSES_FIELD_NUMBER = 3;
  private com.google.protobuf.LazyStringList addresses_;
  /**
   * <code>repeated string addresses = 3;</code>
   * @return A list containing the addresses.
   */
  public com.google.protobuf.ProtocolStringList
      getAddressesList() {
    return addresses_;
  }
  /**
   * <code>repeated string addresses = 3;</code>
   * @return The count of addresses.
   */
  public int getAddressesCount() {
    return addresses_.size();
  }
  /**
   * <code>repeated string addresses = 3;</code>
   * @param index The index of the element to return.
   * @return The addresses at the given index.
   */
  public java.lang.String getAddresses(int index) {
    return addresses_.get(index);
  }
  /**
   * <code>repeated string addresses = 3;</code>
   * @param index The index of the value to return.
   * @return The bytes of the addresses at the given index.
   */
  public com.google.protobuf.ByteString
      getAddressesBytes(int index) {
    return addresses_.getByteString(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (processID_ != 0) {
      output.writeInt32(1, processID_);
    }
    if (!getPortMapperAddressBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, portMapperAddress_);
    }
    for (int i = 0; i < addresses_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, addresses_.getRaw(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (processID_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, processID_);
    }
    if (!getPortMapperAddressBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, portMapperAddress_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < addresses_.size(); i++) {
        dataSize += computeStringSizeNoTag(addresses_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getAddressesList().size();
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof damian.tab.dsynchro.proto.InitResponseMessage)) {
      return super.equals(obj);
    }
    damian.tab.dsynchro.proto.InitResponseMessage other = (damian.tab.dsynchro.proto.InitResponseMessage) obj;

    if (getProcessID()
        != other.getProcessID()) return false;
    if (!getPortMapperAddress()
        .equals(other.getPortMapperAddress())) return false;
    if (!getAddressesList()
        .equals(other.getAddressesList())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + PROCESSID_FIELD_NUMBER;
    hash = (53 * hash) + getProcessID();
    hash = (37 * hash) + PORTMAPPERADDRESS_FIELD_NUMBER;
    hash = (53 * hash) + getPortMapperAddress().hashCode();
    if (getAddressesCount() > 0) {
      hash = (37 * hash) + ADDRESSES_FIELD_NUMBER;
      hash = (53 * hash) + getAddressesList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static damian.tab.dsynchro.proto.InitResponseMessage parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static damian.tab.dsynchro.proto.InitResponseMessage parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static damian.tab.dsynchro.proto.InitResponseMessage parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static damian.tab.dsynchro.proto.InitResponseMessage parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static damian.tab.dsynchro.proto.InitResponseMessage parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static damian.tab.dsynchro.proto.InitResponseMessage parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static damian.tab.dsynchro.proto.InitResponseMessage parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static damian.tab.dsynchro.proto.InitResponseMessage parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static damian.tab.dsynchro.proto.InitResponseMessage parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static damian.tab.dsynchro.proto.InitResponseMessage parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static damian.tab.dsynchro.proto.InitResponseMessage parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static damian.tab.dsynchro.proto.InitResponseMessage parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(damian.tab.dsynchro.proto.InitResponseMessage prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   *req-rep socket - portmapper
   * </pre>
   *
   * Protobuf type {@code synchro.InitResponseMessage}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:synchro.InitResponseMessage)
      damian.tab.dsynchro.proto.InitResponseMessageOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return damian.tab.dsynchro.proto.ProtoMessages.internal_static_synchro_InitResponseMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return damian.tab.dsynchro.proto.ProtoMessages.internal_static_synchro_InitResponseMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              damian.tab.dsynchro.proto.InitResponseMessage.class, damian.tab.dsynchro.proto.InitResponseMessage.Builder.class);
    }

    // Construct using damian.tab.dsynchro.proto.InitResponseMessage.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      processID_ = 0;

      portMapperAddress_ = "";

      addresses_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return damian.tab.dsynchro.proto.ProtoMessages.internal_static_synchro_InitResponseMessage_descriptor;
    }

    @java.lang.Override
    public damian.tab.dsynchro.proto.InitResponseMessage getDefaultInstanceForType() {
      return damian.tab.dsynchro.proto.InitResponseMessage.getDefaultInstance();
    }

    @java.lang.Override
    public damian.tab.dsynchro.proto.InitResponseMessage build() {
      damian.tab.dsynchro.proto.InitResponseMessage result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public damian.tab.dsynchro.proto.InitResponseMessage buildPartial() {
      damian.tab.dsynchro.proto.InitResponseMessage result = new damian.tab.dsynchro.proto.InitResponseMessage(this);
      int from_bitField0_ = bitField0_;
      result.processID_ = processID_;
      result.portMapperAddress_ = portMapperAddress_;
      if (((bitField0_ & 0x00000001) != 0)) {
        addresses_ = addresses_.getUnmodifiableView();
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.addresses_ = addresses_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof damian.tab.dsynchro.proto.InitResponseMessage) {
        return mergeFrom((damian.tab.dsynchro.proto.InitResponseMessage)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(damian.tab.dsynchro.proto.InitResponseMessage other) {
      if (other == damian.tab.dsynchro.proto.InitResponseMessage.getDefaultInstance()) return this;
      if (other.getProcessID() != 0) {
        setProcessID(other.getProcessID());
      }
      if (!other.getPortMapperAddress().isEmpty()) {
        portMapperAddress_ = other.portMapperAddress_;
        onChanged();
      }
      if (!other.addresses_.isEmpty()) {
        if (addresses_.isEmpty()) {
          addresses_ = other.addresses_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureAddressesIsMutable();
          addresses_.addAll(other.addresses_);
        }
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      damian.tab.dsynchro.proto.InitResponseMessage parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (damian.tab.dsynchro.proto.InitResponseMessage) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private int processID_ ;
    /**
     * <code>int32 processID = 1;</code>
     * @return The processID.
     */
    @java.lang.Override
    public int getProcessID() {
      return processID_;
    }
    /**
     * <code>int32 processID = 1;</code>
     * @param value The processID to set.
     * @return This builder for chaining.
     */
    public Builder setProcessID(int value) {
      
      processID_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 processID = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearProcessID() {
      
      processID_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object portMapperAddress_ = "";
    /**
     * <code>string portMapperAddress = 2;</code>
     * @return The portMapperAddress.
     */
    public java.lang.String getPortMapperAddress() {
      java.lang.Object ref = portMapperAddress_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        portMapperAddress_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string portMapperAddress = 2;</code>
     * @return The bytes for portMapperAddress.
     */
    public com.google.protobuf.ByteString
        getPortMapperAddressBytes() {
      java.lang.Object ref = portMapperAddress_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        portMapperAddress_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string portMapperAddress = 2;</code>
     * @param value The portMapperAddress to set.
     * @return This builder for chaining.
     */
    public Builder setPortMapperAddress(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      portMapperAddress_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string portMapperAddress = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearPortMapperAddress() {
      
      portMapperAddress_ = getDefaultInstance().getPortMapperAddress();
      onChanged();
      return this;
    }
    /**
     * <code>string portMapperAddress = 2;</code>
     * @param value The bytes for portMapperAddress to set.
     * @return This builder for chaining.
     */
    public Builder setPortMapperAddressBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      portMapperAddress_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.LazyStringList addresses_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    private void ensureAddressesIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        addresses_ = new com.google.protobuf.LazyStringArrayList(addresses_);
        bitField0_ |= 0x00000001;
       }
    }
    /**
     * <code>repeated string addresses = 3;</code>
     * @return A list containing the addresses.
     */
    public com.google.protobuf.ProtocolStringList
        getAddressesList() {
      return addresses_.getUnmodifiableView();
    }
    /**
     * <code>repeated string addresses = 3;</code>
     * @return The count of addresses.
     */
    public int getAddressesCount() {
      return addresses_.size();
    }
    /**
     * <code>repeated string addresses = 3;</code>
     * @param index The index of the element to return.
     * @return The addresses at the given index.
     */
    public java.lang.String getAddresses(int index) {
      return addresses_.get(index);
    }
    /**
     * <code>repeated string addresses = 3;</code>
     * @param index The index of the value to return.
     * @return The bytes of the addresses at the given index.
     */
    public com.google.protobuf.ByteString
        getAddressesBytes(int index) {
      return addresses_.getByteString(index);
    }
    /**
     * <code>repeated string addresses = 3;</code>
     * @param index The index to set the value at.
     * @param value The addresses to set.
     * @return This builder for chaining.
     */
    public Builder setAddresses(
        int index, java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureAddressesIsMutable();
      addresses_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string addresses = 3;</code>
     * @param value The addresses to add.
     * @return This builder for chaining.
     */
    public Builder addAddresses(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureAddressesIsMutable();
      addresses_.add(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string addresses = 3;</code>
     * @param values The addresses to add.
     * @return This builder for chaining.
     */
    public Builder addAllAddresses(
        java.lang.Iterable<java.lang.String> values) {
      ensureAddressesIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, addresses_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string addresses = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearAddresses() {
      addresses_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string addresses = 3;</code>
     * @param value The bytes of the addresses to add.
     * @return This builder for chaining.
     */
    public Builder addAddressesBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      ensureAddressesIsMutable();
      addresses_.add(value);
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:synchro.InitResponseMessage)
  }

  // @@protoc_insertion_point(class_scope:synchro.InitResponseMessage)
  private static final damian.tab.dsynchro.proto.InitResponseMessage DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new damian.tab.dsynchro.proto.InitResponseMessage();
  }

  public static damian.tab.dsynchro.proto.InitResponseMessage getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<InitResponseMessage>
      PARSER = new com.google.protobuf.AbstractParser<InitResponseMessage>() {
    @java.lang.Override
    public InitResponseMessage parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new InitResponseMessage(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<InitResponseMessage> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<InitResponseMessage> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public damian.tab.dsynchro.proto.InitResponseMessage getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

