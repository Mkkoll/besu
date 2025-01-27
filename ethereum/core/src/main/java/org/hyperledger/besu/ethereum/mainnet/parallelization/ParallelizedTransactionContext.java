/*
 * Copyright contributors to Hyperledger Besu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.hyperledger.besu.ethereum.mainnet.parallelization;

import org.hyperledger.besu.datatypes.Wei;
import org.hyperledger.besu.ethereum.processing.TransactionProcessingResult;
import org.hyperledger.besu.ethereum.trie.diffbased.bonsai.cache.BonsaiCachedMerkleTrieLoader;
import org.hyperledger.besu.ethereum.trie.diffbased.common.worldview.accumulator.DiffBasedWorldStateUpdateAccumulator;

import java.util.Objects;
import java.util.Optional;

public final class ParallelizedTransactionContext {
  private final DiffBasedWorldStateUpdateAccumulator<?> transactionAccumulator;
  private final TransactionProcessingResult transactionProcessingResult;
  private final boolean isMiningBeneficiaryTouchedPreRewardByTransaction;
  private final Wei miningBeneficiaryReward;
  private Optional<BonsaiCachedMerkleTrieLoader> bonsaiCachedMerkleTrieLoader;

  public ParallelizedTransactionContext(
      final DiffBasedWorldStateUpdateAccumulator<?> transactionAccumulator,
      final TransactionProcessingResult transactionProcessingResult,
      final boolean isMiningBeneficiaryTouchedPreRewardByTransaction,
      final Wei miningBeneficiaryReward) {
    this.transactionAccumulator = transactionAccumulator;
    this.transactionProcessingResult = transactionProcessingResult;
    this.isMiningBeneficiaryTouchedPreRewardByTransaction =
        isMiningBeneficiaryTouchedPreRewardByTransaction;
    this.miningBeneficiaryReward = miningBeneficiaryReward;
    this.bonsaiCachedMerkleTrieLoader = Optional.empty();
  }

  public DiffBasedWorldStateUpdateAccumulator<?> transactionAccumulator() {
    return transactionAccumulator;
  }

  public TransactionProcessingResult transactionProcessingResult() {
    return transactionProcessingResult;
  }

  public boolean isMiningBeneficiaryTouchedPreRewardByTransaction() {
    return isMiningBeneficiaryTouchedPreRewardByTransaction;
  }

  public Optional<BonsaiCachedMerkleTrieLoader> getBonsaiCachedMerkleTrieLoader() {
    return bonsaiCachedMerkleTrieLoader;
  }

  public void addBonsaiCachedMerkleTrieLoader(
      final BonsaiCachedMerkleTrieLoader bonsaiCachedMerkleTrieLoader) {
    this.bonsaiCachedMerkleTrieLoader = Optional.ofNullable(bonsaiCachedMerkleTrieLoader);
  }

  public Wei miningBeneficiaryReward() {
    return miningBeneficiaryReward;
  }

  @Override
  public boolean equals(final Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    ParallelizedTransactionContext that = (ParallelizedTransactionContext) o;
    return isMiningBeneficiaryTouchedPreRewardByTransaction
            == that.isMiningBeneficiaryTouchedPreRewardByTransaction
        && Objects.equals(transactionAccumulator, that.transactionAccumulator)
        && Objects.equals(transactionProcessingResult, that.transactionProcessingResult)
        && Objects.equals(miningBeneficiaryReward, that.miningBeneficiaryReward)
        && Objects.equals(bonsaiCachedMerkleTrieLoader, that.bonsaiCachedMerkleTrieLoader);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        transactionAccumulator,
        transactionProcessingResult,
        isMiningBeneficiaryTouchedPreRewardByTransaction,
        miningBeneficiaryReward,
        bonsaiCachedMerkleTrieLoader);
  }

  public static class Builder {
    private DiffBasedWorldStateUpdateAccumulator<?> transactionAccumulator;
    private TransactionProcessingResult transactionProcessingResult;
    private boolean isMiningBeneficiaryTouchedPreRewardByTransaction;
    private Wei miningBeneficiaryReward = Wei.ZERO;

    public Builder transactionAccumulator(
        final DiffBasedWorldStateUpdateAccumulator<?> transactionAccumulator) {
      this.transactionAccumulator = transactionAccumulator;
      return this;
    }

    public Builder transactionProcessingResult(
        final TransactionProcessingResult transactionProcessingResult) {
      this.transactionProcessingResult = transactionProcessingResult;
      return this;
    }

    public Builder isMiningBeneficiaryTouchedPreRewardByTransaction(
        final boolean isMiningBeneficiaryTouchedPreRewardByTransaction) {
      this.isMiningBeneficiaryTouchedPreRewardByTransaction =
          isMiningBeneficiaryTouchedPreRewardByTransaction;
      return this;
    }

    public Builder miningBeneficiaryReward(final Wei miningBeneficiaryReward) {
      this.miningBeneficiaryReward = miningBeneficiaryReward;
      return this;
    }

    public ParallelizedTransactionContext build() {
      return new ParallelizedTransactionContext(
          transactionAccumulator,
          transactionProcessingResult,
          isMiningBeneficiaryTouchedPreRewardByTransaction,
          miningBeneficiaryReward);
    }
  }
}
