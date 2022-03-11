package ca.ualberta.smr;

import de.tu_darmstadt.stg.mubench.cli.*;

import static crypto.Runner.getDetectorOutput;

public class Main {

    public static void main(String[] args) throws Exception {
        DetectionStrategy detectionStrategy = (detectorArgs, builder) -> {
            String targetClassPath = detectorArgs.getTargetClassPaths()[0];

            String[] newArgs = new String[]{
                    "--appPath",
                    targetClassPath
            };

            return builder.withFindings(getDetectorOutput(newArgs));
        };

        new MuBenchRunner()
                .withDetectOnlyStrategy(detectionStrategy)
                .withMineAndDetectStrategy(detectionStrategy)
                .run(args);
    }

}
