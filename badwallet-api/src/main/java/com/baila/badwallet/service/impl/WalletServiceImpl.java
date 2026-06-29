package com.baila.badwallet.service.impl;

import com.baila.badwallet.common.PageResponse;
import com.baila.badwallet.dto.request.CreateWalletRequest;
import com.baila.badwallet.dto.response.BalanceResponse;
import com.baila.badwallet.dto.response.WalletResponse;
import com.baila.badwallet.entity.Wallet;
import com.baila.badwallet.exception.BusinessException;
import com.baila.badwallet.mapper.WalletMapper;
import com.baila.badwallet.repository.WalletRepository;
import com.baila.badwallet.service.WalletFinder;
import com.baila.badwallet.service.WalletService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletFinder walletFinder;
    private final WalletMapper walletMapper;

    public WalletServiceImpl(WalletRepository walletRepository,
                             WalletFinder walletFinder,
                             WalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.walletFinder = walletFinder;
        this.walletMapper = walletMapper;
    }

    @Override
    public WalletResponse createWallet(CreateWalletRequest request) {
        if (walletRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new BusinessException("Un portefeuille existe déjà avec ce numéro de téléphone");
        }
        if (walletRepository.existsByCode(request.code())) {
            throw new BusinessException("Un portefeuille existe déjà avec ce code");
        }
        if (walletRepository.existsByEmail(request.email())) {
            throw new BusinessException("Un portefeuille existe déjà avec cet email");
        }

        Wallet wallet = Wallet.builder()
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .code(request.code())
                .balance(request.initialBalance())
                .currency(request.currency())
                .build();

        return walletMapper.toResponse(walletRepository.save(wallet));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<WalletResponse> listWallets(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<WalletResponse> walletPage = walletRepository.findAll(pageable)
                .map(walletMapper::toResponse);
        return PageResponse.fromPage(walletPage);
    }

    @Override
    @Transactional(readOnly = true)
    public WalletResponse getByPhone(String phoneNumber) {
        return walletMapper.toResponse(walletFinder.byPhone(phoneNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public BalanceResponse getBalance(String phoneNumber) {
        return walletMapper.toBalanceResponse(walletFinder.byPhone(phoneNumber));
    }
}
