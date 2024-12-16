package com.example.digitalact.Data.Mapper

import com.example.digitalact.Data.DataBase.Entity.InstallationLocationEntity
import com.example.digitalact.Domains.Model.SpinnerData

class InstallationLocationMapper: EntityMapper<InstallationLocationEntity, SpinnerData> {
    override fun mapToEntity(domainModel: SpinnerData): InstallationLocationEntity = InstallationLocationEntity(
        id = 0,
        title = domainModel.title,
    )

    override fun mapFromEntity(entity: InstallationLocationEntity): SpinnerData = SpinnerData(
        title = entity.title,
    )

}