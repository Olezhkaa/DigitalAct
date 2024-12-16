package com.example.digitalact.Data.Mapper

import com.example.digitalact.Data.DataBase.Entity.FullNameExecutorEntity
import com.example.digitalact.Domains.Model.SpinnerData

class FullNameExecutorMapper : EntityMapper<FullNameExecutorEntity, SpinnerData> {
    override fun mapToEntity(domainModel: SpinnerData): FullNameExecutorEntity = FullNameExecutorEntity(
        id = 0,
        title = domainModel.title,
    )

    override fun mapFromEntity(entity: FullNameExecutorEntity): SpinnerData = SpinnerData(
        title = entity.title,
    )

}