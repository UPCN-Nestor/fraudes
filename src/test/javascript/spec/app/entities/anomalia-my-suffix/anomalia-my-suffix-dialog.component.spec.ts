/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FrTestModule } from '../../../test.module';
import { AnomaliaMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/anomalia-my-suffix/anomalia-my-suffix-dialog.component';
import { AnomaliaMySuffixService } from '../../../../../../main/webapp/app/entities/anomalia-my-suffix/anomalia-my-suffix.service';
import { AnomaliaMySuffix } from '../../../../../../main/webapp/app/entities/anomalia-my-suffix/anomalia-my-suffix.model';
import { InspeccionMySuffixService } from '../../../../../../main/webapp/app/entities/inspeccion-my-suffix';

describe('Component Tests', () => {

    describe('AnomaliaMySuffix Management Dialog Component', () => {
        let comp: AnomaliaMySuffixDialogComponent;
        let fixture: ComponentFixture<AnomaliaMySuffixDialogComponent>;
        let service: AnomaliaMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [AnomaliaMySuffixDialogComponent],
                providers: [
                    InspeccionMySuffixService,
                    AnomaliaMySuffixService
                ]
            })
            .overrideTemplate(AnomaliaMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnomaliaMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnomaliaMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AnomaliaMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.anomalia = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'anomaliaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AnomaliaMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.anomalia = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'anomaliaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
