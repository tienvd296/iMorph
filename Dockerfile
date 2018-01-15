
FROM debian:jessie
MAINTAINER TienVU tienvd296@gmail.com

ENV LC_ALL en_US.UTF-8  
ENV LANG en_US.UTF-8

VOLUME /Users/tienvd296/eclipse-workspace/PhleboMorph
COPY PhleboMorph.jar PhleboMorph.jar

#RUN apt-get update && \
#    apt-get -y upgrade && \
#    apt-get -y install openjfx
#RUN apt-get -y install ksh bison flex gperf libasound2-dev libgl1-mesa-dev \
#    libgstreamer0.10-dev libgstreamer-plugins-base0.10-dev libjpeg-dev \
#    libpng-dev libx11-dev libxml2-dev libxslt1-dev libxt-dev \
#    libxxf86vm-dev pkg-config x11proto-core-dev \
#   x11proto-xf86vidmode-dev libavcodec-dev mercurial \
#    libgtk2.0-dev libgtk-3-dev \
#    libxtst-dev libudev-dev libavformat-dev   
RUN \
    echo "===> add webupd8 repository..."  && \
    echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list  && \
    echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list  && \
    echo "deb http://ftp.de.debian.org/debian jessie main" >> /etc/apt/sources.list && \
    apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886 && \
    apt-get update && \
    apt-get install -y gtk2-engines libxtst6 libxxf86vm1 freeglut3 libxslt1.1 libswt-gtk-3-java  && \
    apt-get update  && \
    \
    echo "===> install Java"  && \
    echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections  && \
    echo debconf shared/accepted-oracle-license-v1-1 seen true | debconf-set-selections  && \
    DEBIAN_FRONTEND=noninteractive  apt-get install -y --force-yes oracle-java8-installer oracle-java8-set-default 
    \
    
RUN apt-get update && apt-get install -y \
	mesa-utils \
	--no-install-recommends \
	&& rm -rf /var/lib/apt/lists/*
# 	apt-get install -y twm xterm xserver-xorg-video-vesa   \
#	apt-get install -y xserver-xorg-video-all -a -N --ui=none --no-kernel-module \
CMD java -jar PhleboMorph.jar
