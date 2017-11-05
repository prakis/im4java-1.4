/**************************************************************************
/* This class implements the basic test of convert.
/*
/* Copyright (c) 2009 by Bernhard Bablok (mail@bablokb.de)
/*
/* This program is free software; you can redistribute it and/or modify
/* it under the terms of the GNU Library General Public License as published
/* by  the Free Software Foundation; either version 2 of the License or
/* (at your option) any later version.
/*
/* This program is distributed in the hope that it will be useful, but
/* WITHOUT ANY WARRANTY; without even the implied warranty of
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
/* GNU Library General Public License for more details.
/*
/* You should have received a copy of the GNU Library General Public License
/* along with this program; see the file COPYING.LIB.  If not, write to
/* the Free Software Foundation Inc., 59 Temple Place - Suite 330,
/* Boston, MA  02111-1307 USA
/**************************************************************************/

package org.im4java.test;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.im4java.core.*;
import org.im4java.process.ArrayListOutputConsumer;
import org.im4java.process.ProcessStarter;

/**
   This class implements the basic test of convert.

   @version $Revision: 1.7 $
   @author  $Author: bablokb $
 
   @since 1.0.0
 */

public class TestCase_25 extends AbstractTestCase {

  //////////////////////////////////////////////////////////////////////////////

    public String filepath = "/Users/karthik/Downloads/test/a.jpg";
  /**
     Return the description of the test.
  */

  public String getDescription() {
    return "simple use of convert";
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Main method. Just calls AbstractTestCase.runTest(), which catches and
     prints exceptions.
  */

  public static void main(String[] args) {
    TestCase_25 tc = new TestCase_25();
    tc.runTest(args);
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Run the test.
  */
  public void run() { // BufferedImage
        initPath();
        IMOperation op = new IMOperation();
        op.addImage(filepath);

        //op.quality(newQ);        
        //op.resize(100);

        op.addImage("jpg:-");
        Stream2BufferedImage s2b = new Stream2BufferedImage();

        ConvertCmd convert = new ConvertCmd("magick");
        convert.setOutputConsumer(s2b);
        try {
            convert.createScript("magick-createBufferedImage.sh.txt", op);
            //System.out.println(givenPercentageInt + "<--<");
            convert.run(op);
        } catch (Exception e) {
            System.out.println(filepath);            
            e.printStackTrace();
        }
        BufferedImage img = s2b.getImage();
        System.out.println("BufferedImage wxh = "+img.getWidth() + "x"+ img.getHeight());
  }
  private void initPath(){
        ProcessStarter.setGlobalSearchPath("/usr/local/bin/");
        //ProcessStarter.setGlobalSearchPath("/usr/local/Cellar/imagemagick/7.0.7-9/bin/");
      
      System.out.println("----4----");      
  }
  public void runIdentify() throws Exception {

        initPath();
      
        IdentifyCmd identify = new IdentifyCmd("magick");
        IMOperation op = new IMOperation();
        //op.addImage(filedata.getOriginalFilePath());
        //op.verbose();
        //op.addImage(1);
        op.addImage(filepath);
        op.addRawArgs("-format", "%Q");
        //op.format("%Q");
        ArrayListOutputConsumer output = new ArrayListOutputConsumer();
        identify.setOutputConsumer(output);
        //identify.setCommand("-format %Q ");

        identify.createScript("magick-magickAssumedQuality-2.sh.txt", op);
        //identify.run(op, filedata.getOriginalFilePathForMagick());
        identify.run(op);

        ArrayList<String> cmdOutput = output.getOutput();
        //String qual = "100"; // default to max
        for (String line : cmdOutput) {
            System.out.println("------%-->"+line+"<--");
            //qual = line;
        }    
  }
  public void runOriginal() throws Exception {
    System.err.println(" 1. Testing convert ...");
 
    // setup optional control-variables
    boolean induceError = false;
    if (iArgs != null && iArgs.length > 0) {
      induceError = Boolean.parseBoolean(iArgs[0]);
    }

    IMOperation op = new IMOperation();
    op.addImage();
    if (!induceError) {
      // with induceError == true, we will have more images than placeholders
      op.addImage();
    }
    op.bordercolor("darkgray");
    op.border(10,10);
    op.appendHorizontally();
    op.addImage(iTmpImage);

    String[] images = new String[] {
        iImageDir+"tulip1.jpg",
        iImageDir+"tulip 2.jpg"
    };

    ConvertCmd convert = new ConvertCmd();
    convert.createScript(iImageDir+"append.sh",op);
    convert.run(op,(Object[]) images);
    DisplayCmd.show(iTmpImage);
  }
}
