package com.malomnogo.presentation.core.mapper

import com.malomnogo.domain.ConditionDomain

class BaseConditionDomainMapper : ConditionDomain.Mapper<String> {

    override fun mapSuccess(text: String, iconUrl: String) = iconUrl

    override fun mapError(message: String) = ""
}