package com.example.digitalact.Data.Mapper

import com.example.digitalact.Data.DataBase.Entity.TypeNewPUEntity
import com.example.digitalact.Domains.Model.SpinnerData

class TypeNewPUMapper : EntityMapper<TypeNewPUEntity, SpinnerData> {
    override fun mapToEntity(domainModel: SpinnerData): TypeNewPUEntity = TypeNewPUEntity(
        id = 0,
        title = domainModel.title,
    )

    override fun mapFromEntity(entity: TypeNewPUEntity): SpinnerData = SpinnerData(
        title = entity.title,
    )

}