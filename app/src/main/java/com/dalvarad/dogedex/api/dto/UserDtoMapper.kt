package com.dalvarad.dogedex.api.dto

import com.dalvarad.dogedex.models.User

class UserDtoMapper {

    fun fromUserDTOToUserDomain(uSerDTO: USerDTO) =
        User(uSerDTO.id, uSerDTO.email, uSerDTO.authenticationToken)


}