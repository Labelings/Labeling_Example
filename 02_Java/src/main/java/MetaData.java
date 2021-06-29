import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

class MetaData {
    String date;
    Integer revision;
    String author;

    @Override
    public String toString() {
        return "MetaData{" +
                "date='" + date + '\'' +
                ", revision=" + revision +
                ", author='" + author + '\'' +
                '}';
    }

    public static class MetaDataCodec implements Codec<MetaData> {

        @Override
        public MetaData decode(BsonReader reader, DecoderContext decoderContext) {
            MetaData metaData = new MetaData();
        /*
        The data is saved as a document, comparable to a JSON object.
        Each element must be read in the same order it is in the object.
         */
            reader.readStartDocument();
            metaData.date = reader.readString("date");
            metaData.revision = reader.readInt32("revision");
            metaData.author = reader.readString("author");
            reader.readEndDocument();
            return metaData;
        }

        @Override
        public void encode(BsonWriter writer, MetaData value, EncoderContext encoderContext) {
            writer.writeStartDocument();
            writer.writeString("date", value.date);
            writer.writeInt32("revision", value.revision);
            writer.writeString("author", value.author);
            writer.writeEndDocument();
        }

        @Override
        public Class<MetaData> getEncoderClass() {
            return MetaData.class;
        }
    }
}
