package com.example.tvh.utils

import com.google.protobuf.ByteString
import java.nio.ByteBuffer
import java.util.*

fun newUUIDasByteString(): ByteString {
    val uuid = UUID.randomUUID()

    return convertUUIDtoByteString(uuid)
}

fun convertUUIDtoByteString(uuid: UUID): ByteString {
    val bb: ByteBuffer = ByteBuffer.wrap(ByteArray(16))
    bb.putLong(uuid.mostSignificantBits)
    bb.putLong(uuid.leastSignificantBits)

    return ByteString.copyFrom(bb.array())
}