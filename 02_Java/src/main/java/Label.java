import com.indago.data.segmentation.LabelData;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class Label {

    String id;
    String rgb;
    String parentId;

    @Override
    public String toString() {
        return "Label{" +
                "id='" + id + '\'' +
                ", rgb='" + rgb + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }

    public static class LabelCodec implements Codec<Label> {
        @Override
        public Label decode(BsonReader reader, DecoderContext decoderContext) {
            Label label = new Label();
            reader.readStartDocument();
            label.id = reader.readString("id");
            label.rgb = reader.readString("rgb");
            label.parentId = reader.readString("parentId");
            reader.readEndDocument();
            return label;
        }

        @Override
        public void encode(BsonWriter writer, Label value, EncoderContext encoderContext) {
            writer.writeStartDocument();
            writer.writeString("id", value.id);
            writer.writeString("rgb", value.rgb);
            writer.writeString("parentId", value.parentId);
            writer.writeEndDocument();
        }

        @Override
        public Class<Label> getEncoderClass() {
            return Label.class;
        }
    }
}
