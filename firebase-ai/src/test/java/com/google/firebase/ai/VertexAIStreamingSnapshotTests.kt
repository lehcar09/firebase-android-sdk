/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.firebase.ai

import com.google.firebase.ai.type.BlockReason
import com.google.firebase.ai.type.FinishReason
import com.google.firebase.ai.type.HarmCategory
import com.google.firebase.ai.type.InvalidAPIKeyException
import com.google.firebase.ai.type.PromptBlockedException
import com.google.firebase.ai.type.ResponseStoppedException
import com.google.firebase.ai.type.SerializationException
import com.google.firebase.ai.type.ServerException
import com.google.firebase.ai.type.TextPart
import com.google.firebase.ai.util.goldenVertexStreamingFile
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.http.HttpStatusCode
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withTimeout
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class VertexAIStreamingSnapshotTests {
  private val testTimeout = 5.seconds

  @Test
  fun `short reply`() =
    goldenVertexStreamingFile("streaming-success-basic-reply-short.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) {
        val responseList = responses.toList()
        responseList.isEmpty() shouldBe false
        responseList.first().candidates.first().finishReason shouldBe FinishReason.STOP
        responseList.first().candidates.first().content.parts.isEmpty() shouldBe false
        responseList.first().candidates.first().safetyRatings.isEmpty() shouldBe false
      }
    }

  @Test
  fun `long reply`() =
    goldenVertexStreamingFile("streaming-success-basic-reply-long.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) {
        val responseList = responses.toList()
        responseList.isEmpty() shouldBe false
        responseList.forEach {
          it.candidates.first().finishReason shouldBe FinishReason.STOP
          it.candidates.first().content.parts.isEmpty() shouldBe false
          it.candidates.first().safetyRatings.isEmpty() shouldBe false
        }
      }
    }

  @Test
  fun `unknown enum in safety ratings`() =
    goldenVertexStreamingFile("streaming-success-unknown-safety-enum.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) {
        val responseList = responses.toList()

        responseList.isEmpty() shouldBe false
        responseList.any {
          it.candidates.any { it.safetyRatings.any { it.category == HarmCategory.UNKNOWN } }
        } shouldBe true
      }
    }

  @Test
  fun `invalid safety ratings during image generation`() =
    goldenVertexStreamingFile("streaming-success-image-invalid-safety-ratings.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) {
        val responseList = responses.toList()

        responseList.isEmpty() shouldBe false
      }
    }

  @Test
  fun `unknown enum in finish reason`() =
    goldenVertexStreamingFile("streaming-failure-unknown-finish-enum.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) {
        val exception = shouldThrow<ResponseStoppedException> { responses.collect() }
        exception.response.candidates.first().finishReason shouldBe FinishReason.UNKNOWN
      }
    }

  @Test
  fun `quotes escaped`() =
    goldenVertexStreamingFile("streaming-success-quotes-escaped.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) {
        val responseList = responses.toList()

        responseList.isEmpty() shouldBe false
        val part = responseList.first().candidates.first().content.parts.first() as? TextPart
        part.shouldNotBeNull()
        part.text shouldContain "\""
      }
    }

  @Test
  fun `prompt blocked for safety`() =
    goldenVertexStreamingFile("streaming-failure-prompt-blocked-safety.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) {
        val exception = shouldThrow<PromptBlockedException> { responses.collect() }
        exception.response?.promptFeedback?.blockReason shouldBe BlockReason.SAFETY
      }
    }

  @Test
  fun `prompt blocked for safety with message`() =
    goldenVertexStreamingFile("streaming-failure-prompt-blocked-safety-with-message.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) {
        val exception = shouldThrow<PromptBlockedException> { responses.collect() }
        exception.response?.promptFeedback?.blockReason shouldBe BlockReason.SAFETY
        exception.response?.promptFeedback?.blockReasonMessage shouldBe "Reasons"
      }
    }

  @Test
  fun `empty content`() =
    goldenVertexStreamingFile("streaming-failure-empty-content.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) { shouldThrow<SerializationException> { responses.collect() } }
    }

  @Test
  fun `http errors`() =
    goldenVertexStreamingFile(
      "streaming-failure-http-error.txt",
      HttpStatusCode.PreconditionFailed
    ) {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) { shouldThrow<ServerException> { responses.collect() } }
    }

  @Test
  fun `stopped for safety`() =
    goldenVertexStreamingFile("streaming-failure-finish-reason-safety.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) {
        val exception = shouldThrow<ResponseStoppedException> { responses.collect() }
        exception.response.candidates.first().finishReason shouldBe FinishReason.SAFETY
      }
    }

  @Test
  fun `citation parsed correctly`() =
    goldenVertexStreamingFile("streaming-success-citations.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) {
        val responseList = responses.toList()
        responseList.any {
          it.candidates.any { it.citationMetadata?.citations?.isNotEmpty() ?: false }
        } shouldBe true
      }
    }

  @Test
  fun `stopped for recitation`() =
    goldenVertexStreamingFile("streaming-failure-recitation-no-content.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) {
        val exception = shouldThrow<ResponseStoppedException> { responses.collect() }
        exception.response.candidates.first().finishReason shouldBe FinishReason.RECITATION
      }
    }

  @Test
  fun `image rejected`() =
    goldenVertexStreamingFile("streaming-failure-image-rejected.txt", HttpStatusCode.BadRequest) {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) { shouldThrow<ServerException> { responses.collect() } }
    }

  @Test
  fun `unknown model`() =
    goldenVertexStreamingFile("streaming-failure-unknown-model.txt", HttpStatusCode.NotFound) {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) { shouldThrow<ServerException> { responses.collect() } }
    }

  @Test
  fun `invalid api key`() =
    goldenVertexStreamingFile("streaming-failure-api-key.txt", HttpStatusCode.BadRequest) {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) { shouldThrow<InvalidAPIKeyException> { responses.collect() } }
    }

  @Test
  fun `invalid json`() =
    goldenVertexStreamingFile("streaming-failure-invalid-json.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) { shouldThrow<SerializationException> { responses.collect() } }
    }

  @Test
  fun `malformed content`() =
    goldenVertexStreamingFile("streaming-failure-malformed-content.txt") {
      val responses = model.generateContentStream("prompt")

      withTimeout(testTimeout) { shouldThrow<SerializationException> { responses.collect() } }
    }
}
