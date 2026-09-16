JAVAC := javac
JAVA := java
JAVA_RELEASE := 17
SOURCE_DIR := src
OUTPUT_DIR := out
MAIN_CLASS := Main
SOURCES := $(wildcard $(SOURCE_DIR)/*.java)
COMPILE_MARKER := $(OUTPUT_DIR)/.compiled

.PHONY: all compile run clean

all: run

compile: $(COMPILE_MARKER)

$(COMPILE_MARKER): $(SOURCES) Makefile
	@mkdir -p $(OUTPUT_DIR)
	$(JAVAC) --release $(JAVA_RELEASE) -encoding UTF-8 -d $(OUTPUT_DIR) $(SOURCES)
	@touch $(COMPILE_MARKER)

run: compile
	$(JAVA) -cp $(OUTPUT_DIR) $(MAIN_CLASS)

clean:
	rm -rf $(OUTPUT_DIR)
