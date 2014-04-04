#!/usr/bin/env python
# -*- coding: utf8 -*-

import os
from fabric.api import local,run, env, settings,cd,hide
from fabric.contrib.files import exists
from fabric.operations import get 
from fabric.operations import put


def getUserHome():
    with settings(hide("running","stdout","stderr")):
        home = run('echo $HOME')
    return str(home)

def backup(something):
    backhome = getUserHome() + '/backup_dir'
    bn = os.path.basename(something)
    if not exists(backhome):
        run('mkdir -p %s' % backhome)
    with settings(warn_only=True):
        run('rm -rf %s/%s.bk' % (backhome,bn))
        run('mv %s %s/%s.bk' % (something,backhome,bn))

def installGingko():
    app_home = getUserHome() + "/gingko"
    tmp_dir = app_home + "_new"
    if exists(tmp_dir):
        run("rm -rf " + tmp_dir)
    run("mkdir -p " + tmp_dir)
    local("rm -rf ut")
    put("./*",tmp_dir)
    #put("./*",tmp_dir,mirror_local_mode=True)
    if exists(app_home):
        stopGingko()
        backup(app_home)
    run("mv %s %s" % (tmp_dir,app_home))
    startGingko()

def __ctl__(sc):
    app_home = getUserHome() + "/gingko"
    with cd(app_home):
        run("setsid bash " + sc)

def startGingko():
    __ctl__("start.sh")

def stopGingko():
    __ctl__("shutdown.sh")

