# AI-Body-Rehabilitation
A portable non-contact AI auxiliary diagnosis method and system for rehabilitation training of upper and lower limbs after orthopedic surgery based on BlazePose human key point recognition

Operating environment:

Linux version 4.15.0-194-generic (buildd@lcy02-amd64-052) (gcc version 7.5.0 (Ubuntu 7.5.0-3ubuntu1~18.04)) #205-Ubuntu SMP Fri Sep 16 19:49:27 UTC 2022

build guide:

cai@ubuntu:~$ cd mediapipe

cai@ubuntu:~/mediapipe$ bazel build -c opt --config=android_arm64 mediapipe/examples/android/src/java/com/google/mediapipe/apps/AI-HealthCare:AI-HealthCare

Starting local Bazel server and connecting to it...
... still trying to connect to local Bazel server after 10 seconds ...
... still trying to connect to local Bazel server after 20 seconds ...
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'com_google_absl' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'com_google_benchmark' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'pybind11_bazel' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'com_google_protobuf' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'com_google_googletest' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'com_github_gflags_gflags' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'build_bazel_rules_apple' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'build_bazel_rules_swift' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'build_bazel_apple_support' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'xctestrunner' because it already exists.
DEBUG: /home/cai/.cache/bazel/_bazel_cai/34f26c7a46940047bf81de8beb194ab2/external/org_tensorflow/third_party/repo.bzl:124:14: 
Warning: skipping import of repository 'pybind11' because it already exists.
WARNING: /home/cai/mediapipe/mediapipe/framework/tool/BUILD:184:24: in cc_library rule //mediapipe/framework/tool:field_data_cc_proto: target '//mediapipe/framework/tool:field_data_cc_proto' depends on deprecated target '@com_google_protobuf//:cc_wkt_protos': Only for backward compatibility. Do not use.
WARNING: /home/cai/mediapipe/mediapipe/framework/BUILD:54:24: in cc_library rule //mediapipe/framework:calculator_cc_proto: target '//mediapipe/framework:calculator_cc_proto' depends on deprecated target '@com_google_protobuf//:cc_wkt_protos': Only for backward compatibility. Do not use.
INFO: Analyzed target //mediapipe/examples/android/src/java/com/google/mediapipe/apps/AI-HealthCare:AI-HealthCare (198 packages loaded, 14647 targets configured).
INFO: Found 1 target...
Target //mediapipe/examples/android/src/java/com/google/mediapipe/apps/AI-HealthCare:AI-HealthCare up-to-date:
  bazel-bin/mediapipe/examples/android/src/java/com/google/mediapipe/apps/AI-HealthCare/AI-HealthCare_deploy.jar
  bazel-bin/mediapipe/examples/android/src/java/com/google/mediapipe/apps/AI-HealthCare/AI-HealthCare_unsigned.apk
  bazel-bin/mediapipe/examples/android/src/java/com/google/mediapipe/apps/AI-HealthCare/AI-HealthCare.apk
INFO: Elapsed time: 547.622s, Critical Path: 294.79s
INFO: 2 processes: 1 internal, 1 linux-sandbox.
INFO: Build completed successfully, 2 total actions

cai@ubuntu:~/mediapipe$ 

build guide end;
