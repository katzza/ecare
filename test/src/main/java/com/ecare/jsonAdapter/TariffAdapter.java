package com.ecare.jsonAdapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.ecare.domain.TariffEntity;

import java.io.IOException;

public class TariffAdapter extends TypeAdapter<TariffEntity> {

    @Override
    public void write(JsonWriter writer, TariffEntity tariff) throws IOException {
        writer.beginObject();
        writer.name("name").value(tariff.getTariffName());
        writer.name("description").value(tariff.getTariffDescription());
        writer.name("price").value(tariff.getPrice());
        writer.endArray();
        writer.endObject();
        writer.close();
    }

    @Override
    public TariffEntity read(JsonReader reader) throws IOException {
        return null;
    }

}
