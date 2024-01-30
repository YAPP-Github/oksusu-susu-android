package com.susu.feature.received.ledgersearch;

import com.susu.domain.usecase.ledger.GetLedgerListUseCase;
import com.susu.domain.usecase.ledgerrecentsearch.DeleteLedgerRecentSearchUseCase;
import com.susu.domain.usecase.ledgerrecentsearch.GetLedgerRecentSearchListUseCase;
import com.susu.domain.usecase.ledgerrecentsearch.UpsertLedgerRecentSearchUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class LedgerSearchViewModel_Factory implements Factory<LedgerSearchViewModel> {
  private final Provider<UpsertLedgerRecentSearchUseCase> upsertLedgerRecentSearchUseCaseProvider;

  private final Provider<GetLedgerRecentSearchListUseCase> getLedgerRecentSearchListUseCaseProvider;

  private final Provider<DeleteLedgerRecentSearchUseCase> deleteLedgerRecentSearchUseCaseProvider;

  private final Provider<GetLedgerListUseCase> getLedgerListUseCaseProvider;

  public LedgerSearchViewModel_Factory(
      Provider<UpsertLedgerRecentSearchUseCase> upsertLedgerRecentSearchUseCaseProvider,
      Provider<GetLedgerRecentSearchListUseCase> getLedgerRecentSearchListUseCaseProvider,
      Provider<DeleteLedgerRecentSearchUseCase> deleteLedgerRecentSearchUseCaseProvider,
      Provider<GetLedgerListUseCase> getLedgerListUseCaseProvider) {
    this.upsertLedgerRecentSearchUseCaseProvider = upsertLedgerRecentSearchUseCaseProvider;
    this.getLedgerRecentSearchListUseCaseProvider = getLedgerRecentSearchListUseCaseProvider;
    this.deleteLedgerRecentSearchUseCaseProvider = deleteLedgerRecentSearchUseCaseProvider;
    this.getLedgerListUseCaseProvider = getLedgerListUseCaseProvider;
  }

  @Override
  public LedgerSearchViewModel get() {
    return newInstance(upsertLedgerRecentSearchUseCaseProvider.get(), getLedgerRecentSearchListUseCaseProvider.get(), deleteLedgerRecentSearchUseCaseProvider.get(), getLedgerListUseCaseProvider.get());
  }

  public static LedgerSearchViewModel_Factory create(
      Provider<UpsertLedgerRecentSearchUseCase> upsertLedgerRecentSearchUseCaseProvider,
      Provider<GetLedgerRecentSearchListUseCase> getLedgerRecentSearchListUseCaseProvider,
      Provider<DeleteLedgerRecentSearchUseCase> deleteLedgerRecentSearchUseCaseProvider,
      Provider<GetLedgerListUseCase> getLedgerListUseCaseProvider) {
    return new LedgerSearchViewModel_Factory(upsertLedgerRecentSearchUseCaseProvider, getLedgerRecentSearchListUseCaseProvider, deleteLedgerRecentSearchUseCaseProvider, getLedgerListUseCaseProvider);
  }

  public static LedgerSearchViewModel newInstance(
      UpsertLedgerRecentSearchUseCase upsertLedgerRecentSearchUseCase,
      GetLedgerRecentSearchListUseCase getLedgerRecentSearchListUseCase,
      DeleteLedgerRecentSearchUseCase deleteLedgerRecentSearchUseCase,
      GetLedgerListUseCase getLedgerListUseCase) {
    return new LedgerSearchViewModel(upsertLedgerRecentSearchUseCase, getLedgerRecentSearchListUseCase, deleteLedgerRecentSearchUseCase, getLedgerListUseCase);
  }
}
