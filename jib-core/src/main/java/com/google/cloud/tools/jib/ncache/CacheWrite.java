/*
 * Copyright 2018 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.cloud.tools.jib.ncache;

import com.google.cloud.tools.jib.blob.Blob;
import com.google.cloud.tools.jib.image.DescriptorDigest;
import java.util.Optional;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * Represents layer data to write to the cache. <b>Implementation is immutable and thread-safe.</b>
 */
@Immutable
class CacheWrite {

  /**
   * Constructs a {@link CacheWrite} with only the layer {@link Blob}.
   *
   * @param layerBlob the layer {@link Blob}
   * @return the new {@link CacheWrite}
   */
  static CacheWrite layerOnly(Blob layerBlob) {
    return new CacheWrite(layerBlob, null, null);
  }

  /**
   * Constructs a {@link CacheWrite} with a layer {@link Blob}, an additional selector digest, and a
   * metadata {@link Blob}.
   *
   * @param layerBlob the layer {@link Blob}
   * @param selector the selector digest
   * @param metadataBlob the metadata {@link Blob}
   * @return the new {@link CacheWrite}
   */
  static CacheWrite withSelectorAndMetadata(
      Blob layerBlob, DescriptorDigest selector, Blob metadataBlob) {
    return new CacheWrite(layerBlob, selector, metadataBlob);
  }

  private final Blob layerBlob;
  @Nullable private final DescriptorDigest selector;
  @Nullable private final Blob metadataBlob;

  private CacheWrite(
      Blob layerBlob, @Nullable DescriptorDigest selector, @Nullable Blob metadataBlob) {
    this.layerBlob = layerBlob;
    this.selector = selector;
    this.metadataBlob = metadataBlob;
  }

  /**
   * Gets the {@link Blob} to write as the layer contents.
   *
   * @return the layer {@link Blob}
   */
  Blob getLayerBlob() {
    return layerBlob;
  }

  /**
   * Gets the optional selector digest to also reference this layer data. A selector digest may be a
   * secondary identifier for a layer that is distinct from the default layer digest.
   *
   * <p>For example, it is useful as an inexpensive alternative reference to a layer compared to
   * calculating the primary layer digest (SHA256 of compressed tarball).
   *
   * @return the selector digest
   */
  Optional<DescriptorDigest> getSelector() {
    return Optional.ofNullable(selector);
  }

  /**
   * Gets the optional {@link Blob} to write as the arbitrary layer metadata.
   *
   * <p>For example, the metadata could contain last modified time, layer types, layer sources, etc.
   *
   * @return the metadata {@link Blob}
   */
  Optional<Blob> getMetadataBlob() {
    return Optional.ofNullable(metadataBlob);
  }
}
