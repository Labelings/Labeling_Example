import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.labeling.LabelingIOService;
import net.imglib2.labeling.data.Container;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.type.numeric.integer.IntType;
import org.scijava.Context;

import java.io.IOException;

public class JavaExampleRunner {

    public void load_show_save() throws IOException {
        Context context = new Context();
        LabelingIOService labelingIOService = context.getService(LabelingIOService.class);

        /*
            Load a dataset created in 01_Python from the notebook.
            This is the easiest way to load a dataset based on primitive values, here Integer.
         */
        ImgLabeling<Integer, IntType> imgLabeling = labelingIOService.load("../01_Python/example.bson");

        /*
            Output the label information and the related image
         */
        System.out.println(imgLabeling.getMapping().getLabels().toString());
        ImageJFunctions.show( imgLabeling.getIndexImg() );
        /*
            Save the data again at a different place.
         */
        labelingIOService.save(imgLabeling, "../02_Java/example.bson");

    }

    public void load_show_save2() throws IOException {
        Context context = new Context();
        LabelingIOService labelingIOService = context.getService(LabelingIOService.class);

        /*
            Load a dataset created in 01_Python from the notebook.
            This is the easiest way to load a dataset based on primitive values, here Integer.
            Here, we get a container in return which holds the ImgLabeling and the specified MetaData class in
            the arguments. To fill the MetaData class, a Codec is needed, which needs to be provided by the user of
            this library.
         */
        Container<MetaData, Integer, IntType> container = labelingIOService.
                loadWithMetadata("../01_Python/example2.bson", MetaData.class, new MetaData.MetaDataCodec());

        /*
            Output the label information and the related image
         */
        System.out.println(container.getImgLabeling().getMapping().getLabels().toString());
        ImageJFunctions.show( container.getImgLabeling().getIndexImg() );
        System.out.println(container.getMetadata().toString());
        /*
            Save the data again at a different place.
         */
        labelingIOService.saveWithMetaData(container, "../02_Java/example2.bson", MetaData.class,
                new MetaData.MetaDataCodec());

    }


    /*
    This example contains a class as a label, not a primitive (Integer in the examples above), as well as MetaData.
    This marks the most complex way to use this library.
     */
    public void load_show_save3() throws IOException {
        Context context = new Context();
        LabelingIOService labelingIOService = context.getService(LabelingIOService.class);

        /*
            Load a dataset created in 01_Python from the notebook.
            This is the easiest way to load a dataset based on primitive values, here Integer.

            Here, we get a container in return which holds the ImgLabeling and the specified MetaData class in
            the arguments. To fill the MetaData class, a Codec is needed, which needs to be provided by the user of
            this library.
         */
        Container<MetaData, Label, IntType> container = labelingIOService.
                loadWithMetadata("../02_Java/example3.bson", Label.class, MetaData.class, new Label.LabelCodec(), new MetaData.MetaDataCodec());

        /*
            Output the label information and the related image
         */
        System.out.println(container.getImgLabeling().getMapping().getLabels().toString());
        ImageJFunctions.show( container.getImgLabeling().getIndexImg() );
        System.out.println(container.getMetadata().toString());
        /*
            Save the data again at a different place.
         */
        //labelingIOService.saveWithMetaData(container, "../02_Java/example3.bson", Label.class, MetaData.class,
        //        new Label.LabelCodec(), new MetaData.MetaDataCodec());

    }



    public static void main(String... args) throws IOException {
        //new JavaExampleRunner().load_show_save();
        //new JavaExampleRunner().load_show_save2();
        new JavaExampleRunner().load_show_save3();
    }


}
